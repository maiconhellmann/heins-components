# heins-components
It is a library of common components. Impelments follow the guidelines of Google Materials Design and always with a clean and intuitive layout.

  <img src="https://github.com/maiconhellmann/heins-components/blob/master/docs/img/ss3.png" alt="Heins Components Demo" width="200px">
  <img src="https://github.com/maiconhellmann/heins-components/blob/master/docs/img/ss2.png" alt="Heins Components Demo" width="200px">


## Sample Project
You can download the latest sample APK from this repo here: https://github.com/maiconhellmann/heins-components/blob/master/app/app-release.apk

It's also on Google Play:

<a href="https://play.google.com/store/apps/details?id=br.com.forusers.heinscomponents">
    <img src="https://play.google.com/intl/en_us/badges/images/badge_new.png"/>
</a>

Having the sample project installed is a good way to be notified of new releases. Although Watching this repository will allow GitHub to email you whenever I publish a release.

## Gradle Dependency
### Repository
The Gradle dependency not yet available on JCenter.

The minimum API level supported by this library is API 13 (Android 3.1 Honeycomb).

### Dependency
Project _build.gradle_:
```
allprojects {
    repositories {
        // ... other repositories here
        maven { url "http://dl.bintray.com/hellmannmaicon/android" }
    }
}
```
Module/app _build.gradle_:
```
dependencies {
    // ... other dependencies here
    compile 'br.com.forusers.heinscomponents:heinscomponents:0.0.2'
}
```

## GlideImageView
It is a component that controls loading and displaying the image on a remote server  

#### Usage
```
<br.com.forusers.heinscomponents.GlideImageView
        android:layout_width="match_parent"
        android:layout_height="150dp"
        android:layout_margin="5dp"
        />
```

```
GlideImageView glideImageView = (GlideImageView)findViewById(R.id.glideImageView);
glideImageView.setImageUri("http://www.w3schools.com/css/img_fjords.jpg");
```

## MaterialsItemRow
```
<br.com.forusers.heinscomponents.MaterialsItemRow
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    app:leftImage="@drawable/ic_attachment"
                    app:hint="Attachment"
                    app:text="Yourfile.pdf"
                    />
```

<img src="https://github.com/maiconhellmann/heins-components/blob/master/docs/img/itemrow.png" alt="Heins Components Demo" width="200px">

## MaterialsSwitcherRow
```
<br.com.forusers.heinscomponents.MaterialsSwitcherRow
                    android:layout_width="match_parent"
                    android:layout_height="wrap_content"
                    app:primaryText="@string/attachment"
                    app:leftImage="@drawable/ic_attachment"
                    app:secondaryText="@string/attachment_file"
                    />
```
<img src="https://github.com/maiconhellmann/heins-components/blob/master/docs/img/switcher.png" alt="Heins Components Demo" width="200px">

## Donation
You can support the project and thank the author for his hard work.

<a href='https://pledgie.com/campaigns/33080'><img alt='Click here to lend your support to: Heins Components Project and make a donation at pledgie.com !' src='https://pledgie.com/campaigns/33080.png?skin_name=chrome' border='0' ></a>

**PayPal**

<a href="https://www.paypal.com/cgi-bin/webscr?cmd=_s-xclick&hosted_button_id=HUA4MXUNW3TSW">
  <img src="https://www.paypalobjects.com/pt_BR/BR/i/btn/btn_donateCC_LG.gif"/>
</a>

## Contributing
Want to contribute? You are welcome! 
Note that all pull request should go to `dev` branch.

Developed By
------------

* Maicon Hellmann - <maiconhellmann@gmail.com>
