package com.appollo41.loop.networking

import kotlinx.serialization.Serializable

@Serializable
enum class NostrEventKind(val value: Int) {
    Metadata(value = 0),
    ShortTextNote(value = 1),
    RecommendRelay(value = 2),
    FollowList(value = 3),
    EncryptedDirectMessages(value = 4),
    EventDeletion(value = 5),
    Reposts(value = 6),
    Reaction(value = 7),
    BadgeAward(value = 8),
    ChannelCreation(value = 40),
    ChannelMetadata(value = 41),
    ChannelMessage(value = 42),
    ChannelHideMessage(value = 43),
    ChannelMuteUser(value = 44),
    FileMetadata(value = 1063),
    Reporting(value = 1984),
    ZapRequest(value = 9734),
    Zap(value = 9735),
    MuteList(value = 10_000),
    PinList(value = 10_001),
    RelayListMetadata(value = 10_002),
    BookmarksList(value = 10_003),
    WalletInfo(value = 13_194),
    ClientAuthentication(value = 22_242),
    WalletRequest(value = 23_194),
    WalletResponse(value = 23_195),
    NostrConnect(value = 24_133),
    CategorizedPeopleList(value = 30_000),
    CategorizedBookmarkList(value = 30_001),
    ProfileBadges(value = 30_008),
    BadgeDefinition(value = 30_009),
    LongFormContent(value = 30_023),
    ApplicationSpecificData(value = 30_078),
    Unknown(value = -1),
    ;

    companion object {
        fun valueOf(value: Int): NostrEventKind = enumValues<NostrEventKind>().find { it.value == value } ?: Unknown
    }
}
