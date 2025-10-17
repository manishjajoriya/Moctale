package com.manishjajoriya.moctale.domain.usecase

data class MoctaleApiUseCase(
    val exploreUseCase: ExploreUseCase,
    val contentUseCase: ContentUseCase,
    val personUseCase: PersonUseCase,
)
