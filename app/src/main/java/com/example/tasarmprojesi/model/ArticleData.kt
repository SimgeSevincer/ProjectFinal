package com.example.tasarmprojesi.model

import android.content.Context

class ArticleData(context: Context) {

    private val dbHelper = DbHelper(context)

    private val articles = listOf(
        Article(
            "1",
            "Sağlık",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQXE_jqv2oAftW9Do8dhv9ehJHhnskG0ukDynF5BjTZR5eDKbuawC8POfBAiAATi1F99FQ&usqp=CAU",
            "Vegan Diyet",
            "Vegan keto diyeti çok düşük karbonhidratlı, yüksek yağlı ve orta proteinli bir diyettir. Keto diyeti genellikle hayvansal gıdalar açısından zengin olarak değerlendirilir ancak vegan beslenme modeline de uyarlanabilir. Bu diyette kalorilerinizin %75-80'ini yağlardan, %10-20'sini proteinden ve %5-10'unun karbonhidratlardan alırsınız.",
            "2024-03-29",
            10
        ),
        Article(
            "2",
            "Sağlık",
            "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcQ-ajfWl2m_S5CikL0kB47PK5nXGuRXKcPUgw&s",
            "İsveç Diyeti",
            "Sabah kahvaltılarını birkaç günün dışında genel olarak sütsüz ya da bir çay bardağı yağsız süt ile hazırlanmış granül kahvenin oluşturduğu İsveç diyetinde öğlen ve akşam yemeklerinin ilk günlerinde protein kaynağı olarak sadece iki adet haşlanmış yumurta yer alırken ilerleyen günlerde yerini ağırlıklı olarak et, balık ve tavuk ürünlerinin yanında sınırsız olarak alınabilen az yağlı salata oluşturur. Meyve grubu ürünleri belirtildiği üzere ve miktarda alınmalıdır.",
            "2024-02-28",
            5
        ),
        Article(
            "3",
            "Spor",
            "https://www.macfit.com/wp-content/uploads/2022/08/spora-yeni-baslayanlar-2.jpg",
            "Yeni Başlayanlar İçin Spor",
            "Spora başlamak için yürüyüş, koşu gibi aktiviteler veya fitness salonlarını tercih edebilirsiniz. Sporu devamlı hale getirmek ve rutin bir parça yapmak önemlidir. Zamanı doğru ayarlayarak, uygun aktiviteler seçmek önemlidir. Egzersiz seçerken kişisel fiziksel özelliklerinizi ve keyif aldığınız aktiviteleri göz önünde bulundurun. Motivasyonu artırmak için grup derslerine katılabilir veya eğlenceli aktiviteler tercih edebilirsiniz.",
            "2024-03-27",
            15
        ),
        Article(
            "4",
            "Sağlık",
            "https://www.kidsgourmet.com.tr/wp-content/uploads/2018/08/%C3%87%C3%B6lyak-d%C4%B1%C5%9F%C4%B1-gl%C3%BCten-696x461.jpg",
            "Glutensiz Diyet",
            "Toplumda işlenmiş gıdaların hayatımızdan çıkarılmasını söylerken gluteni hayatımızdan çıkardığımızda daha kolay zayıflama algısı ya da daha sağlıklı olma düşüncesi tamamen yanlıştır. Sağlıklı bir kişinin glütensiz beslenmesi yarar yerine ileriki dönemde kişiye zarar vermektedir",
            "2024-05-25",
            12
        )
    )


    fun addArticlesToDb() {
        for (article in articles) {
            dbHelper.addArticle(article)
        }

    }
    fun getArticlesFromDb(): List<Article> {
        return dbHelper.getAllArticles()
    }
}
