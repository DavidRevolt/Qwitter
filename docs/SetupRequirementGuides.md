# Qwitter - Setup Requirement Guides
  
## Guides
- [How to add SHA-1 to android project](https://stackoverflow.com/questions/39144629/how-to-add-sha-1-to-android-application) 


## Firebase Storage rules example
```
storage rules
service firebase.storage {
  match /b/{bucket}/o {
    match /{allPaths=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```

## Firebase Cloud Firestore rules example[Native mode]
```
service cloud.firestore {
  match /databases/{database}/documents {
    match /{document=**} {
      allow read, write: if request.auth != null;
    }
  }
}
```
