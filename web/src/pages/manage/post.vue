<script lang="ts">
import {defineComponent} from 'vue'

export default defineComponent({
  name: "post",
  data () {
    return {
      videoFile: null,
      title: '',
      description: '',
      category: null,
      tags: '',
      thumbnail: null,
      thumbnailPreview: null,
      visibility: 'public',
      uploading: false,

      // 验证规则
      titleRules: [
        v => !!v || '标题不能为空',
        v => (v && v.length <= 100) || '标题不能超过100个字符'
      ],
      videoRules: [
        v => !!v || '请选择视频文件',
        v => !v || v.size < 500000000 || '视频大小不能超过500MB'
      ],
      categoryRules: [v => !!v || '请选择分类'],

      // 示例分类
      categories: [
        '科技',
        '教育',
        '娱乐',
        '音乐',
        '体育',
        '游戏'
      ]
    }
  },
  methods: {
    handleVideoUpload(file) {
      if (file) {
        console.log('Selected video:', file);
        // 这里可以添加文件验证逻辑
      }
    },

    handleThumbnailUpload(file) {
      if (file) {
        const reader = new FileReader();
        reader.onload = e => {
          this.thumbnailPreview = e.target.result;
        };
        reader.readAsDataURL(file);
      }
    },

    async submit() {
      if (!this.$refs.form.validate()) return;

      this.uploading = true;
      try {
        const formData = new FormData();
        formData.append('video', this.videoFile);
        formData.append('thumbnail', this.thumbnail);
        formData.append('title', this.title);
        formData.append('description', this.description);
        formData.append('category', this.category);
        formData.append('tags', this.tags.split(','));
        formData.append('visibility', this.visibility);

        // 这里调用上传API
        // await axios.post('/api/upload', formData, {
        //   headers: { 'Content-Type': 'multipart/form-data' }
        // });

        console.log('Upload data:', Object.fromEntries(formData));
        alert('上传成功！');
      } catch (error) {
        console.error('上传失败:', error);
        alert('上传失败，请重试');
      } finally {
        this.uploading = false;
      }
    }
  }
})
</script>

<template>
  <v-main class="bg-grey-lighten-3">
    <v-container class="mt-4">
      <v-card class="mx-auto" max-width="800">
        <v-card-title class="headline">上传新视频</v-card-title>

        <!-- 上传区域 -->
        <v-card-text>
          <v-file-input
            v-model="videoFile"
            label="选择视频文件"
            accept="video/*"
            prepend-icon="mdi-video"
            @change="handleVideoUpload"
            :rules="videoRules"
            required
            outlined
            class="mb-4"
          ></v-file-input>

          <!-- 视频信息表单 -->
          <v-form ref="form">
            <v-text-field
              v-model="title"
              label="标题"
              :rules="titleRules"
              required
              outlined
              class="mb-4"
            ></v-text-field>

            <v-textarea
              v-model="description"
              label="描述"
              rows="3"
              outlined
              class="mb-4"
            ></v-textarea>

            <v-row>
              <v-col cols="8">
                <v-select
                  v-model="category"
                  :items="categories"
                  label="分类"
                  :rules="categoryRules"
                  required
                  outlined
                ></v-select>
              </v-col>
              <v-col cols="4">
                <v-text-field
                  v-model="tags"
                  label="标签（逗号分隔）"
                  outlined
                ></v-text-field>
              </v-col>
            </v-row>

            <!-- 缩略图上传 -->
            <v-file-input
              v-model="thumbnail"
              label="缩略图"
              accept="image/*"
              prepend-icon="mdi-image"
              @change="handleThumbnailUpload"
              outlined
              class="mb-4"
            ></v-file-input>

            <!-- 缩略图预览 -->
            <v-img
              v-if="thumbnailPreview"
              :src="thumbnailPreview"
              max-height="200"
              contain
              class="mb-4"
            ></v-img>

            <!-- 可见性设置 -->
            <v-radio-group v-model="visibility" row class="mt-4">
              <v-radio label="公开" value="public"></v-radio>
              <v-radio label="未公开" value="unlisted"></v-radio>
              <v-radio label="私密" value="private"></v-radio>
            </v-radio-group>
          </v-form>
        </v-card-text>

        <!-- 提交按钮 -->
        <v-card-actions>
          <v-btn
            color="primary"
            @click="submit"
            :loading="uploading"
            class="ml-2"
          >
            提交
          </v-btn>
        </v-card-actions>
      </v-card>
    </v-container>
  </v-main>
</template>

<style scoped lang="sass">

</style>
