<!-- Tham khảo toàn bộ bên binance, không việc gì phải nghĩ mới -->
<!-- bỏ slider đi -->
<template>
    <div class="h-full p-5 text-white">
        <ul class="w-fit flex">
            <li v-for="(tab, tabIndex) in tabs" :key="tabIndex" :class="[
                    'shrink-0 mr-10 text-center font-semibold text-xl text-soft-gray cursor-pointer duration-200',
                    { 'text-white': selectedTab === tab?.id }
                ]" @click="selectedTab = tab?.id">
                {{ tab?.label }}
            </li>
            <div></div>
        </ul>

        <div class="h-full">
            <router-view/>
        </div>
    </div>
</template>

<script setup lang="ts">
import { useRouter } from 'vue-router';
import { ref, watch } from 'vue';

const tabs = [
    { id: "favorite", label: "Yêu thích" },
    { id: "crypto", label: "Tiền mã hóa" },
    { id: "spot", label: "Giao ngay" },
    { id: "future", label: "Hợp đồng tương lai" },
];
const router = useRouter();
const selectedTab = ref<string>("favorite");

watch(selectedTab, (newVal) => {
    router.push(`/market/${newVal}`);
});
</script>