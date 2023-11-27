<script setup>
import { ref, onMounted } from "vue";
import { listMention, updateMention } from "@/api/mention";

const mentions = ref([]);
const message = ref("");

onMounted(() => {
    listMention(
        ({ data }) => {
            console.log(data);
            message.value = data.msg;
            mentions.value = data.mentionList;
        },
        (err) => {
            console.log(err);
        }
    )
})

function clickMention(mention) {
    updateMention(
        {
            contentid: mention.contentid,
            title: mention.title
        },
        ({data}) => {
            console.log(data.msg);
        },
        (err) => {
            console.log(err);
        }
    )
}

</script>

<template>
    <div v-if="message !== `mentionList OK`">
        <h3>{{ message }}</h3>
    </div>
    <div v-if="message === `mentionList OK`">
        <table class="table">
            <thead>
                <tr class="text-conter">
                    <th scope="col">여행 계획</th>
                    <th scope="col">나를 언급한 사람</th>
                </tr>
            </thead>
            <tbody>
                <tr class="text-conter" v-for="mention in mentions.filter((m)=> !m.checked)" :key="mention.contentid">
                    <td>
                        <router-link :to="{name:'plan-view', params:{contentid:mention.contentid}}"  @click.native="clickMention(mention)">
                            {{ mention.title }}
                        </router-link>
                    </td>
                    <td>{{ mention.userid }}</td>
                </tr>
            </tbody>
        </table>
    </div>
</template>

<style scoped>

</style>