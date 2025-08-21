<template>
    <div class="text-white">
        <ul class="flex">
            <li
                v-for="(tab, tabIndex) in tabs"
                :key="tab?.id"
                :class="[
                    'shrink-0 mr-3 py-2 px-5 text-soft-gray font-semibold rounded-sm cursor-pointer duration-200 hover:text-white',
                    selectedTab === tab?.id ? 'text-white bg-gray-hover' : 'hover:bg-soft-gray-hover'
                ]"
                @click="selectedTab = tab?.id"
            >
                {{ tab?.label }}
            </li>
        </ul>

        <CryptoList/>
    </div>
</template>

<script setup lang="ts">
import { ref, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';
import CryptoList from './CryptoList.vue';
import { onMounted } from 'vue';

const tabs = [
    { id: "all", label: "Tất cả" },
    { id: "bnb-chain", label: "BNB Chain" },
    { id: "solana", label: "Solana" },
    { id: "rwa", label: "RWA" },
    { id: "meme", label: "Meme" },
    { id: "ai", label: "AI" },
    { id: "metaverse", label: "Metaverse" },
    { id: "gaming", label: "Gaming" },
];
const router = useRouter();
const route = useRoute();
const selectedTab = ref<string>(route.query.category as string || 'all');

// đồng bộ khi query thay đổi
watch(
    () => route.query.category,
    (newCategory) => {
        if (route.name === 'market-crypto' && typeof newCategory === 'string') {
            selectedTab.value = newCategory;
        }
    }
);

// cập nhật query category khi chuyển tab
watch(selectedTab, (newVal) => {
    if (route.name === 'market-crypto') {
        router.replace({ query: { category: newVal } });
    }
});

// nếu chưa có category thì mặc định category=all
onMounted(() => {
    if (route.name === 'market-crypto' && !route.query.category) {
        router.replace({ query: { category: 'all' } });
    }
});
</script>