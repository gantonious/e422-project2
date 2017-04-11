#include "filetransfer_encryption_TEAEncryption.h"
#include <stdio.h>

void encrypt(int *, int *);
void decrypt(int *, int *);

JNIEXPORT void JNICALL Java_filetransfer_encryption_TEAEncryption_encrypt
  (JNIEnv *env, jobject object, jbyteArray data_array, jbyteArray key_array) {
    jsize data_length;
    int *data;
    int *key;

    jboolean *is_copy = 0;

    data_length = (*env)->GetArrayLength(env, data_array);
    data = (int *) (*env)->GetByteArrayElements(env, data_array, is_copy);
    key = (int *) (*env)->GetByteArrayElements(env, key_array, is_copy);

    int i = 0;
    int total_passes = data_length / 4;

    for (i = 0; i < total_passes; i+=2) {
        encrypt(data + i, key);
    }

    (*env)->ReleaseByteArrayElements(env, data_array, (jbyte*) data, 0);
}

JNIEXPORT void JNICALL Java_filetransfer_encryption_TEAEncryption_decrypt
  (JNIEnv *env, jobject object, jbyteArray data_array, jbyteArray key_array) {
    jsize data_length;
    int *data;
    int *key;

    jboolean *is_copy = 0;

    data_length = (*env)->GetArrayLength(env, data_array);
    data = (int *) (*env)->GetByteArrayElements(env, data_array, is_copy);
    key = (int *) (*env)->GetByteArrayElements(env, key_array, is_copy);

    int i = 0;
    int total_passes = data_length / 4;

    for (i = 0; i < total_passes; i+=2) {
        decrypt(data + i, key);
    }

    (*env)->ReleaseByteArrayElements(env, data_array, (jbyte*) data, 0);
}

void encrypt (int *v, int *k){
    /* TEA encryption algorithm */
    unsigned int y = v[0], z=v[1], sum = 0;
    unsigned int delta = 0x9e3779b9, n=32;

	while (n-- > 0){
		sum += delta;
		y += (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		z += (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
	}

	v[0] = y;
	v[1] = z;
}

void decrypt (int *v, int *k){
    /* TEA decryption routine */
    unsigned int n=32, sum, y=v[0], z=v[1];
    unsigned int delta=0x9e3779b9l;

	sum = delta<<5;
	while (n-- > 0){
		z -= (y<<4) + k[2] ^ y + sum ^ (y>>5) + k[3];
		y -= (z<<4) + k[0] ^ z + sum ^ (z>>5) + k[1];
		sum -= delta;
	}
	v[0] = y;
	v[1] = z;
}