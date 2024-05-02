package com.vroomvroom.android.data.model.merchant

import com.vroomvroom.android.data.DomainMapper
import com.vroomvroom.android.utils.Constants.DEFAULT_SERVER_TIME_FORMAT
import com.vroomvroom.android.utils.Utils.parseStringToTime

class MerchantMapper : DomainMapper<MerchantDto, Merchant> {
    override fun mapToDomainModel(model: MerchantDto): Merchant {
        return Merchant(
            id = model.id,
            name = model.name,
            image = model.image,
            categories = model.categories.map { it.name },
            productSections = mapToProductSections(model.productSections),
            rates = model.rates,
            ratings = model.ratings,
            favorite = model.favorite,
            opening = model.opening,
            closing = model.closing,
            isOpen = model.isOpen,
            location = model.location,
            reviews = mapToReview(model.reviews)
        )
    }

    private fun mapToProductSections(
        productSectionsDto: List<ProductSectionsDto>?
    ): List<ProductSections>? {
        return productSectionsDto?.map {
            ProductSections(
                id = it.id,
                name = it.name,
                products = mapToProducts(it.products)
            )
        }
    }

    private fun mapToProducts(products: List<ProductDto>): List<Product> {
        return products.map {
            Product(
                id = it.id,
                name = it.name,
                image = it.image,
                price = it.price,
                description = it.description,
                optionSections = mapToOptionSection(it.optionSections)
            )
        }
    }

    private fun mapToOptionSection(options: List<OptionSectionDto>?): List<OptionSections>? {
        return options?.map {
            OptionSections(
                name = it.name,
                required = it.required,
                options = mapToChoices(it.options)
            )
        }
    }

    private fun mapToChoices(choices: List<OptionDto>): List<Option> {
        return choices.map {
            Option(
                name = it.name,
                price = it.price
            )
        }
    }

    private fun mapToReview(reviews: List<ReviewDto>?): List<Review>? {
        return reviews?.map {
            Review(
                id = it.id,
                userId = it.userId,
                rate = it.rate,
                comment = it.comment,
                createdAt = parseStringToTime(it.createdAt, DEFAULT_SERVER_TIME_FORMAT)
            )
        }
    }

    override fun mapToDomainModelList(model: List<MerchantDto>): List<Merchant> {
        return model.map {
            Merchant(
                id = it.id,
                name = it.name,
                image = it.image,
                categories = it.categories.map { item -> item.name },
                productSections = null,
                rates = it.rates,
                ratings = it.ratings,
                favorite = it.favorite,
                opening = it.opening,
                closing = it.closing,
                isOpen = it.isOpen,
                location = null,
                reviews = null
            )
        }
    }

    override fun mapFromDomainModel(model: Merchant): MerchantDto {
        TODO("Not yet implemented")
    }
}