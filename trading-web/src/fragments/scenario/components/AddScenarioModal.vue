<template>
    <div
        class="z-10 w-screen h-screen absolute top-0 left-0 flex justify-center items-center"
    >
        <div
            class="w-screen h-screen absolute top-0 left-0 bg-black opacity-60"
            @click="props.toggleAddScenarioModal"
        ></div>

        <div
            class="z-20 p-5 bg-white rounded-lg"
        >
            <div class="flex justify-end">
                <FontAwesomeIcon
                    :icon="faXmark"
                    class="py-[2px] cursor-pointer duration-200 active:scale-80"
                    @click="props.toggleAddScenarioModal"
                />
            </div>
            <span class="block font-semibold text-xl">Thêm kế hoạch mới</span>
            <div class="flex items-center mt-1">
                <span class="shrink-0 mr-1">Tên kế hoạch:</span>
                <a-input
                    class=""
                    v-model:value="name"
                    placeholder="Aa"
                />
            </div>

            <div class="flex items-center mt-1">
                <span class="shrink-0 mr-1">Mô tả:</span>
                <a-input
                    class=""
                    v-model:value="description"
                    placeholder="Aa"
                />
            </div>

            <a-button
                type="primary"
                class="!w-full !mt-2 !font-semibold"
            >
                Tạo mới
            </a-button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { faXmark } from '@fortawesome/free-solid-svg-icons';
import { watch, ref } from 'vue';
import { usePropsStore } from '../../../stores/propertiesStore';

const name = ref<string>('');
const description = ref<string>('');
const props = usePropsStore();
</script>