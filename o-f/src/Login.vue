<template>
	<div />
</template>

<script>
export default {
	name: 'LegacyLoginRedirect',
	mounted() {
		// 兼容旧入口：统一跳到唯一登录路由
		this.$router?.replace?.('/login')
	}
}
</script>
