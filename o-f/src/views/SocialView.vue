<script setup>
import { computed, ref } from 'vue';
import { useRouter } from 'vue-router';
import { socialPosts } from '../data/social';

import '../../styles/social.css';

const router = useRouter();

const posts = ref(socialPosts);

const viewModels = computed(() => {
  const list = Array.isArray(posts.value) ? posts.value : [];
  return list.map((p) => {
    const nickname = p?.user?.nickname || '用户';
    return {
      id: p?.id,
      nickname,
      avatarText: nickname.charAt(0),
      time: p?.time || '',
      content: p?.content || '',
      likes: Number(p?.likes ?? 0) || 0,
      comments: Number(p?.comments ?? 0) || 0,
      tags: Array.isArray(p?.tags) ? p.tags : [],
    };
  });
});

function onPublish() {
  window.alert('演示版本，该功能暂不可用');
}

function onLike() {
  window.alert('已点赞');
}

function onComment() {
  window.alert('评论功能演示');
}
</script>

<template>
  <div class="social-page">
    <div class="social-header">
      <h1 class="social-title">社交圈</h1>
      <button class="post-btn" type="button" @click="onPublish">发布</button>
    </div>

    <div class="social-tabs">
      <button class="tab-btn active" type="button">动态</button>
      <button class="tab-btn" type="button" @click="router.push('/activities')">活动</button>
    </div>

    <div class="social-content">
      <div class="posts-list">
        <div v-for="post in viewModels" :key="post.id" class="post-card">
          <div class="post-header">
            <div class="post-avatar">{{ post.avatarText }}</div>
            <div class="post-info">
              <div class="post-author">{{ post.nickname }}</div>
              <div class="post-time">{{ post.time }}</div>
            </div>
          </div>

          <div class="post-content">{{ post.content }}</div>

          <div v-if="post.tags.length" class="post-tags">
            <span v-for="tag in post.tags" :key="tag" class="post-tag">#{{ tag }}</span>
          </div>

          <div class="post-actions">
            <button class="post-action" type="button" style="background:none;border:none;padding:0" @click="onLike">👍 {{ post.likes }}</button>
            <button class="post-action" type="button" style="background:none;border:none;padding:0" @click="onComment">💬 {{ post.comments }}</button>
          </div>
        </div>

        <div v-if="!viewModels.length" class="empty-state" style="padding: 16px; color: var(--text-secondary)">暂无动态</div>
      </div>
    </div>
  </div>
</template>
