<template>
    <div class="grow flex justify-center items-center">
        <div
            class="relative py-3 px-8 rounded-full flex items-center bg-dark-area overflow-hidden
            duration-200 hover:bg-soft-gray-hover"
        >
            <div
                class="z-1 absolute top-0 left-0 w-full h-full cursor-pointer"
                @click="isDirect = !isDirect"
            ></div>
            <a-switch v-model:checked="isDirect"/>
            <div class="pl-3 flex flex-col text-left">
                <span class="font-semibold text-lg">Hỏi trực tiếp</span>
                <span class="text-sm font-light text-soft-gray">Mặc định sử dụng AI</span>
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const isDirect = ref<boolean>(false);
</script>