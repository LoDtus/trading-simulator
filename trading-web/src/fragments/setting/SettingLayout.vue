<template>
    <div class="h-full p-5 text-white">
        <h2 class="!font-semibold text-white text-2xl">Cài đặt</h2>

        <!-- theme, ngôn ngữ, bảo mật, xóa tài khoản, xác thực tài khoản -->

        <button
            @click="handleClick"
        >
            Click
        </button>
    </div>
</template>

<script setup lang="ts">
import { watch } from 'vue';
import { useConfirm } from '../../hooks/useConfirm';
import { useWindowSize } from '@vueuse/core';

const confirm = useConfirm();
const { width, height } = useWindowSize();

async function handleClick() {
    const ok = await confirm({
        title: "Xóa dữ liệu",
        message: "Bạn có chắc muốn xóa bản ghi này?",
        confirmText: "Xóa",
        confirmButtonClass: "bg-red-500 hover:bg-red-600"
    });
    if (!ok) return;

    console.log("Đã xác nhận xóa!");
}

watch([width, height], ([newW, newH], [oldW, oldH]) => {
    console.log(`Width: ${oldW} → ${newW}, Height: ${oldH} → ${newH}`)
})

</script>