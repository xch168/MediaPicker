# Android Media Picker

## Download
```groovy
implementation 'com.github.xch168:media-picker:0.0.1'
```

## Usage
To pick video(s):
```kotlin
// pick single
MediaPicker.toPickVideo(this)

// pick multiple
MediaPicker.toPickVideo(this, MediaPicker.CHOICE_MODE_MULTIPLE)
```
To pick image(s):
```kotlin
// pick single
MediaPicker.toPickImage(this)

// pick multiple
MediaPicker.toPickImage(this, MediaPicker.CHOICE_MODE_MULTIPLE)
```
get pick result:
> if you don't config next action, you can get the result in `onActivityResult()` method
```kotlin
MediaPicker.toPickVideo(this)

override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
    super.onActivityResult(requestCode, resultCode, data)
    if (requestCode == MediaPicker.PICK_REQUEST_CODE && resultCode == Activity.RESULT_OK) {
        val media = data?.getParcelableExtra<Media>(MediaPicker.RESULT_MEDIA)
        if (media != null) {
            Glide.with(this).load(media.path).placeholder(R.drawable.mp_ic_thumb_default).into(mThumbView)
            if (media is Media.Video) {
                mDurationView.text = formatTime(media.duration)
                mDurationView.visibility = View.VISIBLE
            } else {
                mDurationView.visibility = View.GONE
            }
        }
    }
}
```
> if you already have nextAction configured. you can get the result on the next page
```kotlin
MediaPicker.toPickVideo(this, MediaPicker.CHOICE_MODE_MULTIPLE, MediaEditorActivity::class.java)

// MediaEditorActivity
val mediaList = intent.getParcelableArrayListExtra<Media>(MediaPicker.RESULT_MEDIA_LIST)
```

License
-------

    Copyright (c) 2020-present. xch168

    Licensed under the Apache License, Version 2.0 (the "License");
    you may not use this file except in compliance with the License.
    You may obtain a copy of the License at

       http://www.apache.org/licenses/LICENSE-2.0

    Unless required by applicable law or agreed to in writing, software
    distributed under the License is distributed on an "AS IS" BASIS,
    WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
    See the License for the specific language governing permissions and
    limitations under the License.