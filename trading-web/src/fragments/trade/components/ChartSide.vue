<template>
    <div class="grow bg-dark-area rounded-sm">
        <div class="px-3 py-2 flex items-center border-b border-gray-line">
            <button
                v-for="(tab, tabIndex) in chartTabs"
                :key="tabIndex"
                :class="[
                    'shrink-0 !mr-5 !font-semibold !text-sm duration-200',
                    selectedChartTab === tab?.id ? '!text-white' : '!text-soft-gray hover:!text-white'
                ]"
                @click="selectedChartTab = tab?.id"
            >
                {{ tab?.label }}
            </button>
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const chartTabs = [
    { id: "chart", label: "Đồ thị" },
    { id: "info", label: "Thông tin" },
    { id: "data", label: "Dữ liệu giao dịch" },
    { id: "analysis", label: "Phân tích giao dịch" },
];
const selectedChartTab = ref<string>('chart');
</script>