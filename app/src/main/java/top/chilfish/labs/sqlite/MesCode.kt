package top.chilfish.labs.sqlite

import top.chilfish.labs.showToast

enum class MesCode {
    SUCCESS,
    DB_FAIL,

    NAME_EMPTY,
    PHONE_EMPTY,

    NAME_EXIST,
    PHONE_EXIST,

    CONTACT_NOT_EXIST,
}

fun showCode(mesCode: MesCode) {
    when (mesCode) {
        MesCode.SUCCESS -> {
            showToast("success")
        }

        MesCode.NAME_EXIST -> {
            showToast("name existed")
        }

        MesCode.PHONE_EXIST -> {
            showToast("phone existed")
        }

        MesCode.NAME_EMPTY -> {
            showToast("name is empty")
        }

        MesCode.PHONE_EMPTY -> {
            showToast("phone is empty")
        }

        MesCode.CONTACT_NOT_EXIST -> {
            showToast("contact not existed")
        }

        else -> {
            showToast("failed")
        }
    }
}