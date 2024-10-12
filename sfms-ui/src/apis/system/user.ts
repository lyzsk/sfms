import type * as T from './type'
import http from '@/utils/http'

export type * from './type'

const BASE_URL = '/system/user'

/** @desc 查询用户列表 */
export function listUser(query: T.UserPageQuery) {
  return http.get<PageRes<T.UserResp[]>>(`${BASE_URL}`, query)
}

/** @desc 查询用户详情 */
export function getUser(id: string) {
  return http.get<T.UserDetailResp>(`${BASE_URL}/${id}`)
}

/** @desc 新增用户 */
export function addUser(data: any) {
  return http.post(`${BASE_URL}`, data)
}

/** @desc 修改用户 */
export function updateUser(data: any, id: string) {
  return http.put(`${BASE_URL}/${id}`, data)
}

/** @desc 删除用户 */
export function deleteUser(ids: string | Array<string>) {
  return http.del(`${BASE_URL}/${ids}`)
}

/** @desc 导出用户 */
export function exportUser(query: T.UserQuery) {
  return http.download(`${BASE_URL}/export`, query)
}

/** @desc 重置密码 */
export function resetUserPwd(data: any, id: string) {
  return http.patch(`${BASE_URL}/${id}/password`, data)
}

/** @desc 下载用户导入模板 */
export function downloadImportUserTemplate() {
  return http.download(`${BASE_URL}/downloadImportUserTemplate`)
}

/** @desc 解析用户导入数据 */
export function parseImportUser(data: FormData) {
  return http.post(`${BASE_URL}/parseImportUser`, data)
}

/** @desc 导入用户 */
export function importUser(data: any) {
  return http.post(`${BASE_URL}/import`, data)
}
