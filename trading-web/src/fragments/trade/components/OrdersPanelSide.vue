<template>
    <div class="h-full mt-1 bg-dark-area rounded-sm">
        <div class="px-3 py-2 flex items-center border-b border-gray-line">
            <button
                v-for="(tab, tabIndex) in ordersPanelTabs"
                :key="tabIndex"
                :class="[
                    'shrink-0 !mr-5 !font-semibold !text-sm duration-200',
                    selectedTab === tab?.id ? '!text-white' : '!text-soft-gray hover:!text-white'
                ]"
                @click="selectedTab = tab?.id"
            >
                {{ tab?.label }}
            </button>
            <div class="grow"></div>
            <div class="text-sm">
                <span>Hide Other Pairs</span>
            </div>
        </div>

        <div class="">
            
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const ordersPanelTabs = [
    { id: "open-orders", label: "Open Orders(0)" },
    { id: "open-history", label: "Order History" },
    { id: "trade-history", label: "Trade History" },
    { id: "funds", label: "Funds" },
    { id: "bots", label: "Bots" },
];
const selectedTab = ref<string>('open-orders');
</script>