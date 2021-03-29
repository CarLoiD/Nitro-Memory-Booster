 # Set JNI local path
LOCAL_PATH := $(call my-dir)

# Clear LOCAL variables
include $(CLEAR_VARS)

# C++ Settings
LOCAL_CPP_EXTENSION := .cpp
LOCAL_CPPFLAGS := -std=c++14
LOCAL_CFLAGS := -Wall -Wextra

# NDK_BUILD Build Settings
LOCAL_MODULE := NitroMemoryBoosterJNI

# NDK_BUILD Link Settings
LOCAL_LDLIBS := -landroid -llog

# C++ Paths
LOCAL_SRC_FILES := $(LOCAL_PATH)/../src/main/cpp/NitroMemoryBooster.cpp

# Build as shared library
include $(BUILD_SHARED_LIBRARY)
