<template>
    <div ref="parent" class="gamemap">
        <canvas ref="canvas" tabindex="0">

        </canvas>
    </div>
</template>

<script>
import { GameMap } from '@/assets/scripts/GameMap';
import { ref } from 'vue';
import { onMounted } from 'vue';
import {useStore} from "vuex";

export default {
    name: "GameMap",  
    components: {

    },
    setup() {
        const store = useStore();
        let parent = ref(null);
        let canvas = ref(null);

        onMounted(() => {
          store.commit("updateGameObject", new GameMap(canvas.value.getContext('2d'), parent.value, store));
        });
        return {
            parent,
            canvas
        }
    }

}
</script>

<style scoped>
.gamemap {
    height: 100%;
    width: 100%;
    display: flex;
    justify-content: center;  
    /* 水平居中 */
    align-items: center;
    /* 竖直居中 */
}
</style>