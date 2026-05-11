// ΑΛΛΑΓΗ ΣΤΗΝ ΣΥΝΑΡΤΗΣΗ openMpvDirect
// Βρες αυτό το κομμάτι στο index.html:

  const a=document.createElement('a');
  a.href=mpvUrl;
  document.body.appendChild(a);
  a.click();
  document.body.removeChild(a);
  toast('▶ MPV: '+ch.name);

// Αντικατέστησέ το με αυτό:

  if(window.Android && window.Android.isAndroid()) {
    // Android: στέλνει το URL στον ExoPlayer
    window.Android.playStream(url, ch.name);
  } else {
    // Windows: ανοίγει MPV όπως πριν
    const a=document.createElement('a');
    a.href=mpvUrl;
    document.body.appendChild(a);
    a.click();
    document.body.removeChild(a);
  }
  toast('▶ ' + ch.name);
