<template>
    <div class="p-5 text-white">
        <button
            class=""
            @click="router.push('/support')"
        >
            Thoát
        </button>
        <span>Chi tiết câu hỏi</span>
    </div>
</template>

<script setup lang="ts">
import { faSquareCaretLeft } from '@fortawesome/free-regular-svg-icons';
import { FontAwesomeIcon } from '@fortawesome/vue-fontawesome';
import { useRouter } from 'vue-router';

const router = useRouter();
</script>