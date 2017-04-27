//
// Created by SamuelGjk on 2017/3/23.
//

#include <jni.h>

#ifndef DIYCODE_NATIVE_SECRET_H
#define DIYCODE_NATIVE_SECRET_H

#ifdef __cplusplus
extern "C" {
#endif

JNIEXPORT jstring JNICALL
Java_moe_yukinoneko_diycode_api_Secret_getSecret(JNIEnv *env, jclass type);

JNIEXPORT jstring JNICALL
Java_moe_yukinoneko_diycode_api_Secret_getClientId(JNIEnv *env, jclass type);

#ifdef __cplusplus
}
#endif

#endif //DIYCODE_NATIVE_SECRET_H
