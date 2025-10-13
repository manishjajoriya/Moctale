package com.manishjajoriya.moctale.usecase

data class MoctaleApiUseCase(
    val exploreUseCase: ExploreUseCase,
    val contentUseCase: ContentUseCase,
    val personUseCase: PersonUseCase,
)
