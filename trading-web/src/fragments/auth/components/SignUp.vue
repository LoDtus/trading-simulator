<template>
    <div class="p-8 bg-white shadow-lg rounded-md">
        <div class="w-full flex justify-end">
            <FontAwesomeIcon
                :icon="faXmark"
                class="py-[2px] cursor-pointer duration-200 active:scale-90"
                @click="router.push('/')"
            />
        </div>
        <h3 class="!font-semibold text-xl">Đăng ký</h3>
        <div class="mb-3">
            <span class="mr-1">Email</span>
            <a-input
                placeholder="example@mail.com"
                v-model:value="email"
            />
        </div>
        <div class="mb-3">
            <span class="mr-1">Mật khẩu</span>
            <a-input-password
                placeholder="Aa"
                v-model:value="password"
            >
                <template #iconRender="v">
                    <EyeTwoTone v-if="v"></EyeTwoTone>
                    <EyeInvisibleOutlined v-else></EyeInvisibleOutlined>
                </template>
            </a-input-password>
        </div>

        <a-button
            type="primary"
            class="w-full !font-semibold"
        >
            Đăng ký
        </a-button>

        <div class="flex items-center">
            <span>Bạn đã có tài khoản?</span>
            <button
                @click="router.push('/auth/sign-in')"
            >
                Đăng nhập
            </button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { EyeTwoTone, EyeInvisibleOutlined } from '@ant-design/icons-vue';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const email = ref<string>('');
const username = ref<string>('');
const password = ref<string>('');
const confirmPassword = ref<string>('');
const rememberMe = ref<boolean>(false);
</script>