<template>
    <a-button
        class="!font-semibold"
        type="primary"
        danger
    >
        Đăng xuất
    </a-button>
</template>

<script setup lang="ts">
import { signOut } from '../../../services/authServices';

const startSignOut = async() => {
    const response = await signOut();
    console.log(response);
};
</script>