<template>
    <div>

    </div>
</template>

<script setup lang="ts">
import { computed, watch } from 'vue';
import { useRoute, useRouter } from 'vue-router';

const route = useRoute();
const router = useRouter();
const category = computed(() => route.query.category as string);

// Lắng nghe khi category thay đổi
watch(category, (newVal) => {
    console.log("Category changed:", newVal);
    // gọi API hoặc filter lại list ở đây
});
</script>