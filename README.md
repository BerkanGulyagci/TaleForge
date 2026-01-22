# StoryTeler - AI Destekli Masal Anlatıcısı

Android uygulaması olarak geliştirilmiş, yapay zeka destekli kişiselleştirilmiş masal anlatıcısı.

## 📱 Özellikler

- **Kişiselleştirilmiş Masallar**: Kullanıcı tercihlerine göre özel masal oluşturma
- **Karakter Seçimi**: 5 farklı masal karakteri (Noel Baba, Peluş Ayı, Peri, Bilge Baykuş, Neşeli Tavşan)
- **Çoklu Tür Desteği**: Macera, Peri Masalı, Eğitici, Komik, Dostluk masalları
- **Yaş Hedeflemesi**: 3-12 yaş arası çocuklar için uygun içerik
- **Uzunluk Seçenekleri**: Kısa (3dk), Orta (7dk), Uzun (15dk) masal seçenekleri

## 🏗️ Mimari

Proje **Clean Architecture** ve **MVVM** mimarisi kullanılarak geliştirilmiştir:

### Katmanlar
- **Presentation**: UI (Jetpack Compose) ve ViewModels
- **Domain**: Business logic, Use Cases ve Models
- **Data**: Repository implementations ve data sources

### Teknolojiler
- **Jetpack Compose** - Modern UI toolkit
- **Hilt** - Dependency Injection
- **Navigation Compose** - Ekranlar arası geçiş
- **StateFlow** - Reactive state management
- **Material 3** - Modern tasarım sistemi

## 📂 Proje Yapısı

```
app/src/main/java/com/berkang/storyteler/
├── data/
│   └── repository/           # Repository implementations
├── domain/
│   ├── model/               # Domain models
│   ├── repository/          # Repository interfaces
│   └── usecase/            # Business logic use cases
├── presentation/
│   ├── navigation/         # Navigation setup
│   └── screens/           # UI screens
│       ├── home/
│       ├── story_setup/
│       ├── character_select/
│       └── story_player/
└── di/                    # Dependency injection modules
```

## 🚀 Kurulum

1. Projeyi klonlayın:
```bash
git clone https://github.com/[username]/StoryTeler.git
```

2. Android Studio'da açın

3. Gradle sync yapın

4. Uygulamayı çalıştırın

## 📋 Gereksinimler

- **Min SDK**: 24 (Android 7.0)
- **Target SDK**: 36
- **Compile SDK**: 36
- **Kotlin**: 2.0.21
- **Gradle**: 8.13.2

## 🎯 Mevcut Durum

### Tamamlanan Özellikler ✅
- Ana ekran tasarımı
- Masal kurulum formu (MVVM ile)
- Navigation yapısı
- Clean Architecture temel yapısı
- Hilt DI entegrasyonu

### Gelecek Özellikler 🔄
- AI entegrasyonu (masal üretimi)
- Karakter seçim ekranı
- Masal oynatıcı (ses çıkışı)
- Masal kütüphanesi
- Offline masal saklama

## 🤝 Katkıda Bulunma

1. Fork yapın
2. Feature branch oluşturun (`git checkout -b feature/amazing-feature`)
3. Değişikliklerinizi commit edin (`git commit -m 'Add amazing feature'`)
4. Branch'inizi push edin (`git push origin feature/amazing-feature`)
5. Pull Request oluşturun

## 📄 Lisans

Bu proje MIT lisansı altında lisanslanmıştır. Detaylar için [LICENSE](LICENSE) dosyasına bakın.

## 👨‍💻 Geliştirici

**Berkan** - [GitHub Profili](https://github.com/[username])

---

⭐ Projeyi beğendiyseniz yıldız vermeyi unutmayın!