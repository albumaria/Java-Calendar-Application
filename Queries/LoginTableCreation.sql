USE CalendarApp

CREATE TABLE Users (
	id INT IDENTITY(1,1) PRIMARY KEY,
	username NVARCHAR(50) UNIQUE NOT NULL,
	password_hash NVARCHAR(255) NOT NULL
)

GO
xp_readerrorlog 0,1, N'Server is listening on'
GO

