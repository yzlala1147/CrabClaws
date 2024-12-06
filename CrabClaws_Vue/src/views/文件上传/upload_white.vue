<template>
    <div>
      <div class="top-left-text" style="color: black; font-weight: bold; text-align: left;">
        文件上传-白名单
      </div>
      <input type="file" ref="fileInput" />
      <button @click="uploadFile">上传文件</button>
      <p v-if="uploadResult">{{ uploadResult }}</p>
    </div>
  </template>
  
  <script>
  
  export default {
    name: 'FileUpload',
    data() {
      return {
        uploadResult: null
      };
    },
    methods: {
      uploadFile() {
        const file = this.$refs.fileInput.files[0];
        if (file) {
          const formData = new FormData();
          formData.append('file', file);
  
          this.$axios.post('api/upload_white', formData, {
            headers: {
              'Content-Type': 'multipart/form-data'
            }
          })
        .then((response) => {
                this.uploadResult = response.data;
              })
        .catch((error) => {
                console.error('文件上传失败', error);
                this.uploadResult = '文件上传失败：' + error.message;
              });
        } else {
          this.uploadResult = '请选择要上传的文件';
        }
      }
    }
  };
  </script>
  
  <style>
  
  </style>