<template>
    <div class="basis-1/3 mt-1 text-white bg-dark-area rounded-sm">
        <div class="px-3 py-2 flex items-center border-b border-gray-line">
            <button
                v-for="(tab, tabIndex) in tradesPanelTabs"
                :key="tabIndex"
                :class="[
                    'shrink-0 !mr-2 !font-semibold !text-sm duration-200',
                    selectedTab === tab?.id ? '!text-white' : '!text-soft-gray hover:!text-white'
                ]"
                @click="selectedTab = tab?.id"
            >
                {{ tab?.label }}
            </button>

            <div class="grow"></div>
            <FontAwesomeIcon
                :icon="faEllipsis"
                class="text-soft-gray cursor-pointer duration-200 hover:text-white active:scale-95"
            />
        </div>
    </div>
</template>

<script setup lang="ts">
import { faEllipsis } from '@fortawesome/free-solid-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { ref } from 'vue';

const tradesPanelTabs = [
    { id: "market-trade", label: "Market Trades" },
    { id: "my-trade", label: "My Trades" },
]
const selectedTab = ref<string>('market-trade');
</script>