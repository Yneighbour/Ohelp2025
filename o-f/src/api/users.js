import { requestData } from './http';

export async function listUsers() {
  return await requestData({
    method: 'GET',
    url: '/user/',
  });
}

export async function createUser(payload) {
  return await requestData({
    method: 'POST',
    url: '/user/',
    data: payload,
  });
}

export async function updateUser(id, payload) {
  return await requestData({
    method: 'PUT',
    url: `/user/${id}`,
    data: payload,
  });
}

export async function deleteUser(id) {
  return await requestData({
    method: 'DELETE',
    url: `/user/${id}`,
  });
}

export async function activateUser(id) {
  return await requestData({
    method: 'PUT',
    url: `/user/${id}/activate`,
  });
}

export async function deactivateUser(id) {
  return await requestData({
    method: 'PUT',
    url: `/user/${id}/deactivate`,
  });
}
