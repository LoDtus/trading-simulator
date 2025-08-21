<template>
    <!-- Sử dụng TradingView Charting Library, tuy nhiên nó lại không có trên npm mà phải tải về -->
    <div class="h-full text-white">
        <ul class="h-fit p-5 flex">
            <li
                v-for="(tab, tabIndex) in tabs"
                :key="tabIndex"
                :class="[
                    'shrink-0 mr-8 !font-semibold text-xl cursor-pointer duration-200',
                    selectedTab === tab?.id ? 'text-white' : 'text-soft-gray hover:text-white'
                ]"
                @click="selectedTab = tab?.id"
            >
                {{ tab?.label }}
            </li>
        </ul>

        <div class="h-full">
            <router-view/>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue'
import { useRouter } from 'vue-router';

const tabs = [
    { id: "spot", label: "Spot" },
    { id: "convert", label: "Chuyển đổi & Giao dịch theo lô" },
];
const selectedTab = ref<string>('spot');
const router = useRouter();

watch(selectedTab, (path) => {
    router.replace(path);
});
</script>