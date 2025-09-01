import { ref, onMounted, onUnmounted } from "vue";

// Lấy ra kích thước của kích thước hiển thị giao diện hiện tại
export function useWindowDimension() {
    const width = ref(window.innerWidth);
    const height = ref(window.innerHeight);

    const updateSize = () => {
        width.value = window.innerWidth;
        height.value = window.innerHeight;
    };

    onMounted(() => {
        window.addEventListener("resize", updateSize);
    });
    onUnmounted(() => {
        window.removeEventListener("resize", updateSize);
    });

    return { width, height };
}
