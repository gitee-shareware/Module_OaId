package com.shareware.oaid

interface IGetter {
    /**
     * @return 成功获取到OAID
     */
    fun onOAIDGetComplete(result: String)
}