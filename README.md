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
