# frame-glide
Glide本地视频帧选择插件

## 使用方法

1. 添加权限
```
<uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />
```

2. 为 `Glide` 添加 `ModelLoader`
```java
Glide.get(/*Context*/context)
                .getRegistry()
                .append(VideoFrame.class,
                        Bitmap.class,
                        new MediaStoreVideoFrameLoader.Factory(/*context*/context));
```

3. 通过 `Glide` 加载视频帧
```java
Glide.with(this)
                .load(new VideoFrame.Builder()
                        .path(/*path*/path)
                        .percent(/*percent*/percent)
                        .build())
                .into(ivFrame);
```

## 关于 `VideoFrame`


## 参考
- [Glide](https://github.com/bumptech/glide)


## LICENSE

```
MIT License

Copyright (c) 2018 Burjal

Permission is hereby granted, free of charge, to any person obtaining a copy
of this software and associated documentation files (the "Software"), to deal
in the Software without restriction, including without limitation the rights
to use, copy, modify, merge, publish, distribute, sublicense, and/or sell
copies of the Software, and to permit persons to whom the Software is
furnished to do so, subject to the following conditions:

The above copyright notice and this permission notice shall be included in all
copies or substantial portions of the Software.

THE SOFTWARE IS PROVIDED "AS IS", WITHOUT WARRANTY OF ANY KIND, EXPRESS OR
IMPLIED, INCLUDING BUT NOT LIMITED TO THE WARRANTIES OF MERCHANTABILITY,
FITNESS FOR A PARTICULAR PURPOSE AND NONINFRINGEMENT. IN NO EVENT SHALL THE
AUTHORS OR COPYRIGHT HOLDERS BE LIABLE FOR ANY CLAIM, DAMAGES OR OTHER
LIABILITY, WHETHER IN AN ACTION OF CONTRACT, TORT OR OTHERWISE, ARISING FROM,
OUT OF OR IN CONNECTION WITH THE SOFTWARE OR THE USE OR OTHER DEALINGS IN THE
SOFTWARE.
```