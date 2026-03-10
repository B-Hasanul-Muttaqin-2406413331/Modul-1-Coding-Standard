# module 1
## Refleksi 1
### clean code practices
- meaningful name: feature edit dan delete telah memenuhi practice meaningful name. fungsi terlihat jelas bagian dari masing-masing feature dapat dikenali dan dapat di-search
- simple functions: feature edit dan delete terdiri dari beberapa functions yang meng-handle hanya sebagian kecil dari feature keseluruhan dengan standar MTC. findById function juga dipisahkan menjadi standalone function terlepas dari edit & delete
- meaningful comments: project tidak memiliki komen sama sekali karena redundan
- error handling: feature belom memenuhi practice error handling karena feature tidak memiliki error handling
- object and data structure: feature edit dan product tidak membuat data structure baru.
### secure coding practice
- authentication & authorization : project tidak memiliki roles dan dapat diakses secara bebas, authentication dan authorization redundan
- input data validation: feature belum memenuhi, tidak ada pengecekan sama sekali dalam input data
- secure session management: project tidak menyimpan data apapun dalam session(ataupun cookie), practice ini redundan.
### how to improve
- menggunakan fitur built in untuk user input validation
- menambahkan error handlinh
- menggunakan findById functions dalam edit dan delete daripada coding ulang fungsi yang sama

## Refleksi 2
### pertanyaan pertama:
pertama-tama, saya merasa panik karena ini sudah j-1. kemudian, diperlukan unit test sebanyak manapun yang diperlukan sehingga code coverage 100% dan ditambahkan sebanyak itu lagi untuk case negative dan ditambahkan lagi untuk setiap edge case. 100% code coverage belum tentu bebas bug dan error, arti dari 100% code coverage pada dasarnya bermakna test telah mencakup setiap line setidaknya sekali. tanpa memedulikan positive dan negative case, belum lagi edge case
### pertanyaan kedua:
tidak. pertama ini melanggar maintenance difficuilty, perubahan pada unit yang di test berarti perubahan pada setiap test yang mengecek unit tersebut. kemudian ini juga melanggar code duplication, configuration logic akan di copy-paste ke banyak file test case. terakhir, readability, karena unit test kepisah-pisah jadi susah buat ditelusuri dan dipahami.

# module 2
## refleksi
### code quality issue
code quality issue yang di laporkan oleh github pmd tools dengan default ruleset(ruleset.xml) adalah UnnecessaryModifier (config/pmd/ruleset.xml:9). strategi saya untuk menyelesaikan masalah inia dalah dengan menggunakan tombol backspace untuk menghapus modifier 'public' di interface service.
### ci/cd
tidak sepenuhnya. untuk ci, requirements sesuai tertulis di module mengenai automated build script using various tool sudah terpenuhi. dengan menggunakan script yml yang menjalankan jacoco dan pmd pada action github, syarat tersebut telah terpenuhi. jika dari diri saya sendiri, mungkin satu-satunya hal yang kurang adalah menambahkan linter ke ci script. selanjutnya untuk cd masih belum terpenuhi, hal ini dikarenakan meskipun fitur autodeploy sudah ada, tetapi cd belum ter-pipeline dengan CI. seharusnya CD langsung fail jika CI tidak terpenuhi, dimana fitur itu absen dari codebase saya. hal ini dikarenakan render webhooks merupakan fitur berbayar, sehingga saya tidak bisa melakukan pipeline antara CI dan CD

# module 3
## refleksi
1. prinsip yang saya terapkan pada project ini terutama adalah beberapa bagian dari SOLID, khususnya single responsibility principle, dependency inversion principle, dan open-closed principle. contohnya, `CarController` tidak lagi mewarisi `ProductController` karena controller untuk car dan product punya tanggung jawab route yang berbeda. selain itu, controller dan service juga mulai diarahkan untuk bergantung pada abstraksi seperti interface service dan interface repository, bukan langsung ke implementasi konkrit. pada model, `Car` juga diubah untuk mewarisi `Product` agar field yang memang sama tidak diduplikasi lagi.
2. keuntungan menerapkan SOLID pada project ini adalah code menjadi lebih mudah dirawat, lebih jelas tanggung jawabnya, dan lebih aman saat dikembangkan. contohnya, ketika `CarController` dilepas dari inheritance yang salah terhadap `ProductController`, perubahan pada route product tidak lagi berisiko memengaruhi route car. lalu, ketika service bergantung pada interface repository, implementasi repository bisa diganti tanpa harus mengubah service. contoh lainnya, saat `Car` mewarisi `Product`, field umum seperti id, nama, dan quantity tidak perlu ditulis dua kali, sehingga perubahan pada struktur data bersama bisa dilakukan lebih konsisten.
3. kerugian jika SOLID tidak diterapkan pada project ini adalah code akan lebih mudah menumpuk duplikasi, lebih rapuh terhadap perubahan, dan lebih sulit dipahami. contohnya, saat `Car` sebelumnya memiliki field sendiri yang sebenarnya sama dengan `Product`, ada duplikasi data dan potensi inkonsistensi penamaan seperti `productId` dan `carID`. contoh lain, jika controller atau service langsung bergantung pada class implementasi, setiap pergantian implementasi akan memaksa perubahan di banyak tempat. inheritance yang tidak tepat seperti `CarController` mewarisi `ProductController` juga membuat struktur project membingungkan karena relasi antar class tidak mencerminkan relasi domain yang sebenarnya.


# module 4
## refleksi tutorial
### Reflect based on Percival (2017) proposed self-reflective questions
1. correctness: test suite untuk order sudah berfungsi secara adequate, dengan minimal satu happy path dan satu unhappy path untuk setiap function. tentunya test ini belum terlalu berfungsi untuk mengawasi edge case, tapi sudah lebih dari cukup untuk default case
2. maintainability: tests hanya peduli pada input dan output dan terabstraksi secara logic dari unit yang di test. hal ini membuat refactoring untuk unit functiopn lebih mudah. tests yang digunakan juga tidak overdone ataupun overcomplicating the unit tested. balance antara unit test, integration test maupun functional test tidak terlalu relevan karena fungsi yang di add di tutorial belum ada integration atau functional layer.
3. productive workflow: yes, setiap unit test bekerja secara cepat dan bisa dijalankan per test, per test class maupun secara keseluruhan. feedback yang diberikan cepat dan spesifik sehingga bagus untuk quick development cycle.

### reflect whether your tests have successfully followed F.I.R.S.T. principle
1. fast: per unit test kurang dari satu detik dan per test class kurang dari 7
2. independent: test bersifat independen satu yang lainnya tanpa menunggu test sebelumnya selesai. order test bisa diubah-ubah tanpa mengganti hasil
3. repeatable: test tidak bergantung pada enviroment variable ataupun enviroment installation
4. self-validating: test lepas tangan, tinggal klik dan cek meraha atau hijau
5. timely: test ditulis dan di-commit sebelum kode
