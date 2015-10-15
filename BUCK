# The files and modifications provided by Facebook are for testing and evaluation purposes only.  Facebook reserves all rights not expressly granted.

import re

jar_deps = []
for jarfile in glob(['libs/*.jar']):
  name = 'jars__' + re.sub(r'^.*/([^/]+)\.jar$', r'\1', jarfile)
  
  #for windows begin
  name = name.replace('libs\\', '')
  name = name.replace('.jar', '')
  #for windows end
  
  jar_deps.append(':' + name)
  prebuilt_jar(
    name = name,
    binary_jar = jarfile,
  )

android_library(
  name = 'all-jars',
  exported_deps = jar_deps,
)

annotation_jar_deps = []
for jarfile in glob(['provided-libs/*.jar']):
  name = 'annotation_jars__' + re.sub(r'^.*/([^/]+)\.jar$', r'\1', jarfile)
  
  #for windows begin
  name = name.replace('libs\\', '')
  name = name.replace('.jar', '')
  #for windows end
  
  annotation_jar_deps.append(':' + name)
  prebuilt_jar(
    name = name,
    binary_jar = jarfile,
  )

android_library(
  name = 'all-annotation-jars',
  exported_deps = annotation_jar_deps,
)


android_build_config(
  name = 'build-config',
  package = 'com.example.butterknife',
)

APP_CLASS_SOURCE = 'butterknife-sample/src/main/java/com/example/butterknife/AppShell.java'

android_library(
  name = 'main-lib',
  srcs = glob(['butterknife-sample/src/main/java/**/*.java'], excludes = [APP_CLASS_SOURCE]),
  deps = [
    ':all-jars',
    ':butterknife',
    ':res'
  ],
  annotation_processors = ['butterknife.compiler.ButterKnifeProcessor'],
  annotation_processor_deps = [':all-annotation-jars'],
  annotation_processor_params = ['com.example.butterknife']
)

android_library(
  name = 'application-lib',
  srcs = [APP_CLASS_SOURCE],
  deps = [
    ':build-config',
    ':jars__buck-android-support',
  ],
)

android_resource(
  name = 'res',
  package = 'com.example.butterknife',
  res = 'butterknife-sample/src/main/res',
  assets = 'butterknife-sample/src/main/assets',
  deps = []
)


android_manifest(
  name = 'manifest',
  skeleton = 'butterknife-sample/src/main/AndroidManifest.xml',
  deps = [
    ':main-lib',
  ],
)

keystore(
  name = 'debug_keystore',
  store = 'keystore/debug.keystore',
  properties = 'keystore/debug.keystore.properties',
)

android_binary(
  name = 'sample',
  manifest = ':manifest',
  keystore = ':debug_keystore',
  use_split_dex = True,
  exopackage = True,
  exopackage_modes = ['secondary_dex'],
  primary_dex_patterns = [
    '^com/example/butterknife/AppShell^',
    '^com/example/butterknife/BuildConfig^',
    '^com/facebook/buck/android/support/exopackage/'
  ],
  deps = [
    ':main-lib',
    ':application-lib',
  ],
)


android_prebuilt_aar(
  name = 'butterknife',
  aar = 'libs/butterknife-8.0.3-SNAPSHOT.aar',
)
