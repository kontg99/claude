# Nova Stream Android — Οδηγίες Εγκατάστασης

## Βήμα 1: Ανέβασε τα αρχεία στο GitHub repo σου

Στο repo σου (kontg99/claude), δημιούργησε φάκελο `android/`
και ανέβασε ΟΛΑ τα αρχεία του project εκεί.

Η δομή πρέπει να είναι:
```
claude/
├── index.html          ← το υπάρχον HTML σου
├── android/
│   ├── build.gradle
│   ├── settings.gradle
│   ├── gradle.properties
│   ├── gradle/wrapper/gradle-wrapper.properties
│   └── app/
│       ├── build.gradle
│       └── src/main/
│           ├── AndroidManifest.xml
│           ├── java/com/novastream/app/
│           │   ├── MainActivity.java
│           │   └── PlayerActivity.java
│           └── res/
│               ├── layout/
│               │   ├── activity_main.xml
│               │   └── activity_player.xml
│               └── values/
│                   └── themes.xml
└── .github/
    └── workflows/
        └── build.yml
```

## Βήμα 2: Αλλαγή στο index.html

Στη συνάρτηση `openMpvDirect`, βρες αυτό:
```js
  const a=document.createElement('a');
  a.href=mpvUrl;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  toast('▶ MPV: '+ch.name);
```

Αντικατέστησέ το με:
```js
  if(window.Android && window.Android.isAndroid()) {
    window.Android.playStream(url, ch.name);
  } else {
    const a=document.createElement('a');
    a.href=mpvUrl;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  }
  toast('▶ ' + ch.name);
```

## Βήμα 3: Ενεργοποίηση GitHub Actions

1. Πήγαινε στο repo σου στο GitHub
2. Κλικ "Actions" tab
3. Αν σε ρωτήσει, κλικ "I understand my workflows, enable them"
4. Κάθε φορά που κάνεις push στον φάκελο android/, το build ξεκινά αυτόματα

## Βήμα 4: Κατέβασε το APK

1. GitHub → repo → Actions
2. Κλικ στο τελευταίο build
3. Scroll κάτω → "Artifacts"
4. Κατέβασε το "nova-stream-debug"
5. Αποσύμπιεσε το .zip → πάρε το .apk

## Βήμα 5: Εγκατάσταση

### Κινητό (Poco X3 NFC):
1. Ρυθμίσεις → Ασφάλεια → "Άγνωστες πηγές" → Ενεργοποίηση
2. Μεταφορά .apk στο κινητό (USB ή Google Drive)
3. Άνοιγμα .apk → Εγκατάσταση

### TV Box (Tanix TX6):
1. Κατέβασε το .apk με browser στο TV box
   ή μεταφορά μέσω USB stick
2. Χρήση File Manager → Άνοιξε το .apk → Εγκατάσταση

## Πώς λειτουργεί

- Ανοίγεις την app → φορτώνει το index.html από GitHub Pages
- Επιλέγεις κανάλι → ανοίγει ο ExoPlayer fullscreen
- Back button → επιστροφή στη λίστα
- Αλλαγές στο HTML → αυτόματα στην app (χωρίς νέο APK)
- Αλλαγές στο Android (σπάνια) → νέο build → νέο APK

## Τι παίζει ο ExoPlayer

✅ HLS (.m3u8)
✅ MPEG-TS (.ts) — αυτό που δεν έπαιζε στον browser Windows
✅ H.264, H.265/HEVC
✅ AC3/EAC3 audio
✅ Σχεδόν ό,τι και VLC/MPV
