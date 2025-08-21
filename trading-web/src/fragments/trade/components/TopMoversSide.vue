<template>
    <div class="basis-1/3 mt-1 bg-dark-area rounded-sm">
        <div class="px-3 py-2 flex items-center border-b border-gray-line">
            <span class="font-semibold text-sm">Top Movers</span>
        </div>

        <div class="!text-[12px] px-1 pt-1">
            <button
                v-for="(tab, tabIndex) in topMoversTabs"
                :key="tabIndex"
                :class="[
                    'shrink-0 !mr-1 !mb-1 py-1 px-3 rounded-sm duration-200 hover:bg-gray-hover',
                    selectedTab === tab?.id ? '!text-white !bg-gray-hover' : '!text-soft-gray hover:!text-white active:scale-95'
                ]"
                @click="selectedTab = tab?.id"
            >
                {{ tab?.label }}
            </button>
        </div>

        <div>
            
        </div>
    </div>
</template>

<script setup lang="ts">
import { ref } from 'vue';

const topMoversTabs = [
    { id: "all", label: "All" },
    { id: "change", label: "Change" },
    { id: "high-low", label: "New High/Low" },
    { id: "fluctuation", label: "Fluctuation" },
    { id: "volume", label: "Volume" },
];
const selectedTab = ref<string>('all');
</script>