<!-- Tìm kiếm các câu hỏi đã có trước đó, hỏi bằng AI, hỏi trực tiếp -->
<template>
    <div class="p-5 text-white">
        <h2 class="!font-semibold text-2xl">Hỗ trợ</h2>

        <a-input
            placeholder="Tìm kiếm"
        />

        <ul>
            <li
                v-for="(question, questionIndex) in questions"
                :key="questionIndex"
                class="p-3 mt-2 bg-dark-area border !border-gray-line rounded-md
                cursor-pointer duration-200 hover:bg-soft-gray-hover active:scale-95"
                @click="router.push('/support/question/123')"
            >
                <div class="text-sm text-soft-gray flex items-center">
                    <span>{{ "01/01/2025" }}</span>
                    <div class="h-3 !w-[1px] mx-2 border-r border-soft-gray"></div>
                    <span>Cập nhật: {{ "01/05/2025" }}</span>
                </div>
                <h3 class="!font-semibold !mb-3 !mt-1">Lorem ipsum dolor sit, amet consectetur adipisicing elit. Nisi, ad excepturi? Saepe id laborum tempora sed et laboriosam rerum dolores eveniet vero ullam voluptas, impedit atque iste! Nemo, rerum autem?</h3>
                
                <div class="inline text-sm text-soft-gray mr-3">
                    <FontAwesomeIcon :icon="faEye" />
                    <span>102</span>
                </div>
                <div class="inline text-sm text-soft-gray">
                    <FontAwesomeIcon :icon="faMessage" />
                    <span>102</span>
                </div>
            </li>
        </ul>

        <button
            class="fixed bottom-10 right-10 py-3 px-8 bg-white rounded-full
            font-semibold !text-black !text-lg shadow-lg
            cursor-pointer duration-200 active:scale-90"
            @click="router.push('/support/ask')"
        >
            <FontAwesomeIcon :icon="faHeadphones" class="mr-1"/>
            <span>Đặt câu hỏi</span>
        </button>
    </div>
</template>

<script setup lang="ts">
import { faEye, faHeadphones, faMessage } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { useRouter } from 'vue-router';

const router = useRouter();
const questions = [1,1,1,1,1,1,1,1];
</script>