import { defineStore } from "pinia";

export const useLocalDataStore = defineStore("localData", {
    state: () => ({
        scenario: [],
        history: [],
        
    }),
    actions: {
        setScenario(data: any) {
            this.scenario = data;
        },
        setHistory(data: any) {
            this.history = data;
        },

        saveAll() {
            localStorage.setItem("localData", JSON.stringify(this.$state));
        },
        loadAll() {
            const temp = localStorage.getItem("localData");
            if (temp) Object.assign(this.$state, JSON.parse(temp));
        },
    },
});