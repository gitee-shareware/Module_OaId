package com.shareware.oaid

interface IGetter {
    /**
     * @return 成功获取到id
     */
    fun onOAIDGetComplete(result: String)

    /**
     * 获取失败（不正常或获取不到）
     * @param error 异常信息
     */
    fun onOAIDGetError(error: Exception)
}