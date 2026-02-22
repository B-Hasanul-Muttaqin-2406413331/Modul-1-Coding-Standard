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
