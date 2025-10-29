package com.manishjajoriya.moctale.domain.usecase

data class MoctaleApiUseCase(
    val validateUseCase: ValidateUseCase,
    val exploreUseCase: ExploreUseCase,
    val contentUseCase: ContentUseCase,
    val activityUseCase: ActivityUseCase,
    val personUseCase: PersonUseCase,
    val scheduleUseCase: ScheduleUseCase,
    val searchUseCase: SearchUseCase,
    val browseUseCase: BrowseUseCase,
)
