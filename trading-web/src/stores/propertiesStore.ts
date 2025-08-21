import { defineStore } from "pinia";

export const usePropsStore = defineStore("properties", {
    state: () => ({
        theme: "dark",
        language: "en",

        openAddScenarioModal: false,
    }),
    actions: {
        setTheme(theme: string) {
            this.theme = theme;
        },
        setLanguage(language: string) {
            this.language = language;
        },
        toggleAddScenarioModal() {
            this.openAddScenarioModal = !this.openAddScenarioModal;
        }
    },
    persist: true
});
