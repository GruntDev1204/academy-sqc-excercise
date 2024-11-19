import { initializeApp } from 'firebase/app'
import { getStorage, FirebaseStorage } from 'firebase/storage'

interface FirebaseConfig {
    apiKey: string
    authDomain: string
    databaseURL: string
    projectId: string
    storageBucket: string
    messagingSenderId: string
    appId: string
    measurementId: string
}

const firebaseConfig: FirebaseConfig = {
    apiKey: "AIzaSyDXmB224izEN_Z3LM-eUhv_9YqIcwA9dIE",
    authDomain: "trung1204-bdc27.firebaseapp.com",
    databaseURL: "https://trung1204-bdc27-default-rtdb.firebaseio.com",
    projectId: "trung1204-bdc27",
    storageBucket: "trung1204-bdc27.appspot.com",
    messagingSenderId: "385196080794",
    appId: "1:385196080794:web:e9294cb656fd590728e923",
    measurementId: "G-91GM5M38PB"
}

const app = initializeApp(firebaseConfig)

const storage: FirebaseStorage = getStorage(app)

export { storage }
