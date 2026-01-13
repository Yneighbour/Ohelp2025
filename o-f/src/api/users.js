import { requestData } from './http';

export async function listUsers() {
  return await requestData({
    method: 'GET',
    url: '/user/',
  });
}
