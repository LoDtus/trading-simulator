<template>
    <div class="h-full flex text-white">
        <div class="basis-[25%] px-2 pb-2 pt-3 border-r border-r-gray-line">
            <button
                class=""
                @click="router.push('/support')"
            >
                Thoát
            </button>

            <div>
                Câu hỏi mới
            </div>
            <ul>
                <li
                    v-for="(ques, quesIndex) in myQuestions"
                    :key="quesIndex"
                    class="mt-1 p-3 bg-dark-area rounded-md
                    cursor-pointer duration-200 hover:bg-soft-gray-hover"
                    @click="router.push(`/support/ask/${quesIndex}`)"
                >
                    <div class="flex justify-between items-start">
                        <span class="!font-semibold line-clamp-2">Lorem, ipsum dolor sit amet consectetur adipisicing elit. Soluta tempore voluptatem quam quis possimus asperiores officiis, nihil corporis nulla, quasi fugiat reprehenderit recusandae expedita dolorem odit provident, dicta culpa vel?</span>
                        <button
                            class="w-[25px] h-[25px] aspect-square rounded-full flex justify-center items-center
                            duration-200 active:scale-90 hover:bg-gray-hover"
                            
                        >
                            <FontAwesomeIcon :icon="faEllipsis" />
                        </button>
                    </div>
                    <div class="mt-1 text-sm text-soft-gray flex items-center">
                        <span>{{ "01/01/2025" }}</span>
                        <div class="h-3 !w-[1px] mx-2 border-r border-soft-gray"></div>
                        <span>Cập nhật: {{ "01/05/2025" }}</span>
                    </div>
                </li>
            </ul>
        </div>
        <div class="basis-[75%] flex flex-col">
            <router-view/>
            <div class="p-3 border-t border-gray-line">
                <a-input
                    placeholder="Aa"
                    v-model:value="newQuestion"
                />
            </div>
        </div>
    </div>
</template>

<script setup lang="ts">
import { faEllipsis } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { ref } from 'vue';
import { useRouter } from 'vue-router';

const router = useRouter();
const myQuestions = [1,1,1,1,1,1,1,1,1];
const isDirect = ref<boolean>(false);
const newQuestion = ref<string>('');
</script>