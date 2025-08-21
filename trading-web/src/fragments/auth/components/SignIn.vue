<template>
    <div class="p-8 bg-white shadow-lg rounded-md">
        <div class="w-full flex justify-end">
            <FontAwesomeIcon
                :icon="faXmark"
                class="py-[2px] cursor-pointer duration-200 active:scale-90"
                @click="router.push('/')"
            />
        </div>
        <h3 class="!font-semibold text-xl">Đăng nhập</h3>
        <div class="mb-3">
            <span class="mr-1">Email hoặc tên đăng nhập</span>
            <a-input
                placeholder="example@mail.com"
                v-model:value="emailOrUsername"
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
            Đăng nhập
        </a-button>

        <div class="flex items-center">
            <span>Bạn chưa có tài khoản?</span>
            <button
                @click="router.push('/auth/sign-up')"
            >
                Đăng ký
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
const emailOrUsername = ref<string>('');
const password = ref<string>('');
</script>