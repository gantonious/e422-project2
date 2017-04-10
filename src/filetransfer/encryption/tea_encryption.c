#include "filetransfer_encryption_TEAEncryption.h"

void encrypt(int *, int *);
void decrypt(int *, int *);

JNIEXPORT void JNICALL Java_filetransfer_encryption_TEAEncryption_encrypt
  (JNIEnv *env, jobject object, jbyteArray data_array, jbyteArray key_array) {
    int data_length;
    int *data;
    int *key;

    jboolean *is_copy = 0;

    data_length = (int) (*env)->GetArrayLength(env, data_array);
    data = (int *) (*env)->GetIntArrayElements(env, data_array, is_copy);
    key = (int *) (*env)->GetIntArrayElements(env, key_array, is_copy);

    encrypt(data, key);

    (*env)->SetByteArrayRegion(env, data_array, 0, (jsize) data_length, (jbyte*) data);
}

JNIEXPORT void JNICALL Java_filetransfer_encryption_TEAEncryption_decrypt
  (JNIEnv *env, jobject object, jbyteArray data_array, jbyteArray key_array) {
    int data_length;
    int *data;
    int *key;

    jboolean *is_copy = 0;

    data_length = (int) (*env)->GetArrayLength(env, data_array);
    data = (int *) (*env)->GetIntArrayElements(env, data_array, is_copy);
    key = (int *) (*env)->GetIntArrayElements(env, key_array, is_copy);

    decrypt(data, key);

    (*env)->SetByteArrayRegion(env, data_array, 0, (jsize) data_length, (jbyte*) data);
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