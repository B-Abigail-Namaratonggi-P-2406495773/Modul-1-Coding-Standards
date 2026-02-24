# Eshop Advance Programming

Name: Abigail Namaratonggi P.

Class: Advance Programming B

Student Number: 2406495773

<details>
<summary><b>Reflection on Module 1</b></summary>
<br>

### Reflection 1
#### 1. Clean Code Principles

**a. Meaningful Names:**
- Memastikan nama-nama method di ProductController bersifat deskriptif dan menjelaskan tujuannya. 
- Nama class (`ProductController`, `ProductServiceImpl`, `ProductRepository`) dan nama method (`createProductPage`, `editProductPost`, `findAll`) sangat deskriptif dan mengikuti domain permasalahan.
- Nama variabel seperti `allProducts`, `productIterator`, dan `existing` (di dalam method edit) jelas dan menjelaskan isinya tanpa perlu komentar tambahan.

**b.Function:**
  - Setiap fungsi dibuat kecil, fokus, dan hanya melakukan satu tugas (Single Responsibility).
  - `createProductPage` hanya mempersiapkan model untuk merender halaman pembuatan produk.
  - `createProductPost` hanya menangani penerimaan data form dan memanggil service.
  - `edit` (di `Service`) hanya fokus mencari produk berdasarkan ID dan memperbarui atributnya jika ditemukan.

**c. Comments:**
  - Kode ditulis sedemikian rupa agar menjelaskan dirinya sendiri (self-explanatory), sehingga mengurangi kebutuhan akan komentar.
  - pada `product.setProductId(UUID.randomUUID().toString())`, kode tersebut sudah jelas tujuannya untuk membuat ID unik tanpa perlu menambahkan komentar manual.
 
**d. Objects and Data Structures**
- Terdapat pemisahan yang jelas antara objek data dan objek behavior.
- Class `Product` (di package `model`) berfungsi sebagai struktur data yang hanya memiliki atribut dan getter/setter (menggunakan Lombok/manual).
- `ProductServiceImpl` dan `ProductRepository` adalah objek yang memiliki behavior untuk mengubah data tersebut.
---

#### 2. Secure Coding Practices

**a. ID yang Aman (mencegah OWASP Broken Access Control):** 
- Menggunakan `UUID.randomUUID()` untuk ID produk. Ini mencegah serangan enumerasi ID (menebak ID urut 1, 2, 3) yang bisa mengekspos data orang lain.

**b. Data Binding:**
- Menggunakan `@ModelAttribute` pada Controller dan Thymeleaf `th:object` di HTML. Ini memastikan input user diproses murni sebagai data, bukan kode yang bisa diexecute.
---

#### 3. Areas for Improvement

**a. CSRF Vulnerability in Delete Feature:**
- Fitur `delete` saat ini masih menggunakan `@GetMapping`. Ini tidak aman (rentan CSRF) dan bisa diubah menjadi `@PostMapping` agar data tidak terhapus tidak sengaja.

**b. Input Data Validation:** 
- Belum ada validasi di sisi server, sehingga pengguna bisa memasukkan nama kosong atau jumlah produk negatif. Kita bisa menggunakan `@NotNull` atau `@Min` pada model `Product`.
---

### Reflection 2

#### Question 1
Setelah menulis unit test, saya merasa lebih percaya diri terhadap keamanan dan stabilitas kode yang saya buat. Namun, saya menyadari bahwa 100% code coverage tidak menjamin aplikasi sepenuhnya bebas dari bug. Oleh karena itu, jumlah unit test dalam suatu class tidak memiliki angka pasti, melainkan harus cukup untuk mencakup positive case, negative case, dan edge case. 

#### Question 2
Pembuatan functional test suite baru dengan menyalin kode lama akan menurunkan kualitas kode karena memunculkan duplikasi. Hal ini menyulitkan pemeliharaan, sebab jika konfigurasi setup berubah, kita harus mengubahnya di setiap file satu per satu. Solusi terbaik adalah mengekstrak logika setup yang sama ke dalam sebuah Base Class agar bisa digunakan ulang oleh test lainnya tanpa menulis ulang kode.
</details>

<details>
<summary><b>Reflection on Module 2</b></summary>

#### Question 1
Masalah kualitas kode yang saya perbaiki adalah isu Maintability pada file `editProduct.html`, di mana elemen `<label>` belum terhubung dengan benar ke kolom inputnya. 
Strategi perbaikan yang saya lakukan adalah: 
* Menggunakan dashboard SonarCloud untuk tahu kolom mana saja yang labelnya bermasalah.
* Mambahkan atribut `id` di bagian `<input>` dan saya pasangkan dengan atribut `for` di tag `<label>` nya (misalnya untuk bagian nama produk dan kuantitas) supaya keduanya saling terhubung.
* Melakukan commit dan push perubahan tersebut ke branch `module-2-exercise` untuk memverifikasi bahwa code smell tersebut hilang pada analisis SonarCloud berikutnya.

#### Question 2
Menurut saya, apa yang sudah diterapkan sekarang sudah masuk kategori CI/CD. Karena setiap kali saya melakukan push ke GitHub, sistem secara otomatis menjalankan GitHub Actions untuk mengetes kode dan mengecek kualitasnya lewat SonarCloud tanpa perlu saya jalankan manual. Ini sudah memenuhi sisi Continuous Integrationnya.

Lalu untuk Continuous Deployment, aplikasinya juga sudah otomatis terupdate di platform PaaS (Koyeb) setiap kali ada perubahan di repositori. Platform tersebut secara otomatis mendeteksi pembaruan pada branch yang terhubung, melakukan proses build menggunakan Dockerfile, dan memperbarui aplikasi di server produksi hingga statusnya menjadi Healthy. Seluruh siklus ini menjamin bahwa setiap kode yang lulus tahap pengujian dapat langsung didistribusikan ke pengguna secara cepat dan konsisten.
</details>